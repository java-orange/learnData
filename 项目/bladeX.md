#### 探索BladeX框架

当前版本： 2.6.1.RELEASE



后端技术栈：

- springboot
- redis
- nginx
- mybatis-plus

前端技术栈：

- avue



#### 7.28 探索树形结构的实现

##### 	全加载树形结构。

数据库设计

```sql
CREATE TABLE `t_emp_rule_cat` (
  `id` bigint(64) NOT NULL COMMENT 'ID',
  `parent_id` bigint(64) DEFAULT '0' COMMENT '上级分类ID',
  `tid` bigint(64) DEFAULT NULL COMMENT '顶级ID',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `cd` varchar(64) DEFAULT NULL COMMENT '分类编码',
  `icon` varchar(1000) DEFAULT NULL COMMENT '图标',
  `img_url` varchar(1000) DEFAULT NULL COMMENT '图片',
  `show_pos` varchar(1000) DEFAULT '' COMMENT '显示位置',
  `sort` int(8) DEFAULT '1' COMMENT '排序',
  `rmks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(64) DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) DEFAULT '0' COMMENT '是否已删除',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='处罚规则类目';
```



返回试图层vo类

```java
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.tool.node.INode;
import org.springblade.modules.punishment.entity.EmpRuleCat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 处罚规则类目视图实体类
 *
 * @author BladeX
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EmpRuleCatVO对象", description = "处罚规则类目")
public class EmpRuleCatVO extends EmpRuleCat implements INode<EmpRuleCatVO> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<EmpRuleCatVO> children;

	/**
	 * 是否有子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Boolean hasChildren;


	@Override
	public List<EmpRuleCatVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}


}

```

通过继承INode类并重写其中的全部方法。

INode

```java
package org.springblade.core.tool.node;

import java.io.Serializable;
import java.util.List;

public interface INode<T> extends Serializable {
    Long getId();

    Long getParentId();

    List<T> getChildren();

    default Boolean getHasChildren() {
        return false;
    }
}

```



在service层进行树形结构的整理

```java
@Override
	public List<EmpRuleCatVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}
```

其中的mapper xml配置

```xml
 <select id="tree" resultMap="treeNodeResultMap">
        select id, parent_id, title, id as "value", id as "key" from t_emp_rule_cat where is_deleted = 0;
    </select>
```

其中的ForestNodeMerger.merge()方法

```java

package org.springblade.core.tool.node;

import java.util.List;
import java.util.function.Consumer;

public class ForestNodeMerger {
    public ForestNodeMerger() {
    }

    public static <T extends INode<T>> List<T> merge(List<T> items) {
        ForestNodeManager<T> forestNodeManager = new ForestNodeManager(items);
        items.forEach((forestNode) -> {
            if (forestNode.getParentId() != 0L) {
                INode<T> node = forestNodeManager.getTreeNodeAt(forestNode.getParentId());
                if (node != null) {
                    node.getChildren().add(forestNode);
                } else {
                    forestNodeManager.addParentId(forestNode.getId());
                }
            }

        });
        return forestNodeManager.getRoot();
    }
}

```

其中涉及到的ForestNodeManager类

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springblade.core.tool.node;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ForestNodeManager<T extends INode<T>> {
    private final ImmutableMap<Long, T> nodeMap;
    private final Map<Long, Object> parentIdMap = Maps.newHashMap();

    public ForestNodeManager(List<T> nodes) {
        this.nodeMap = Maps.uniqueIndex(nodes, INode::getId);
    }

    public INode<T> getTreeNodeAt(Long id) {
        return this.nodeMap.containsKey(id) ? (INode)this.nodeMap.get(id) : null;
    }

    public void addParentId(Long parentId) {
        this.parentIdMap.put(parentId, "");
    }

    public List<T> getRoot() {
        List<T> roots = new ArrayList();
        this.nodeMap.forEach((key, node) -> {
            if (node.getParentId() == 0L || this.parentIdMap.containsKey(node.getId())) {
                roots.add(node);
            }

        });
        return roots;
    }
}

```

其中的ImmutableMap类



```java
/*
 * Copyright (C) 2008 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.CollectPreconditions.checkEntryNotNull;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.WeakOuter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * A {@link Map} whose contents will never change, with many other important properties detailed at
 * {@link ImmutableCollection}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "https://github.com/google/guava/wiki/ImmutableCollectionsExplained">
 * immutable collections</a>.
 *
 * @author Jesse Wilson
 * @author Kevin Bourrillion
 * @since 2.0
 */
@GwtCompatible(serializable = true, emulated = true)
@SuppressWarnings("serial") // we're overriding default serialization
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {

  /**
   * Returns the empty map. This map behaves and performs comparably to
   * {@link Collections#emptyMap}, and is preferable mainly for consistency
   * and maintainability of your code.
   */
  public static <K, V> ImmutableMap<K, V> of() {
    return ImmutableBiMap.of();
  }

  /**
   * Returns an immutable map containing a single entry. This map behaves and
   * performs comparably to {@link Collections#singletonMap} but will not accept
   * a null key or value. It is preferable mainly for consistency and
   * maintainability of your code.
   */
  public static <K, V> ImmutableMap<K, V> of(K k1, V v1) {
    return ImmutableBiMap.of(k1, v1);
  }

  /**
   * Returns an immutable map containing the given entries, in order.
   *
   * @throws IllegalArgumentException if duplicate keys are provided
   */
  public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2) {
    return RegularImmutableMap.fromEntries(entryOf(k1, v1), entryOf(k2, v2));
  }

  /**
   * Returns an immutable map containing the given entries, in order.
   *
   * @throws IllegalArgumentException if duplicate keys are provided
   */
  public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    return RegularImmutableMap.fromEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3));
  }

  /**
   * Returns an immutable map containing the given entries, in order.
   *
   * @throws IllegalArgumentException if duplicate keys are provided
   */
  public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    return RegularImmutableMap.fromEntries(
        entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
  }

  /**
   * Returns an immutable map containing the given entries, in order.
   *
   * @throws IllegalArgumentException if duplicate keys are provided
   */
  public static <K, V> ImmutableMap<K, V> of(
      K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    return RegularImmutableMap.fromEntries(
        entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
  }

  // looking for of() with > 5 entries? Use the builder instead.

  /**
   * Verifies that {@code key} and {@code value} are non-null, and returns a new
   * immutable entry with those values.
   *
   * <p>A call to {@link Map.Entry#setValue} on the returned entry will always
   * throw {@link UnsupportedOperationException}.
   */
  static <K, V> ImmutableMapEntry<K, V> entryOf(K key, V value) {
    return new ImmutableMapEntry<K, V>(key, value);
  }

  /**
   * Returns a new builder. The generated builder is equivalent to the builder
   * created by the {@link Builder} constructor.
   */
  public static <K, V> Builder<K, V> builder() {
    return new Builder<K, V>();
  }

  static void checkNoConflict(
      boolean safe, String conflictDescription, Entry<?, ?> entry1, Entry<?, ?> entry2) {
    if (!safe) {
      throw new IllegalArgumentException(
          "Multiple entries with same " + conflictDescription + ": " + entry1 + " and " + entry2);
    }
  }

  /**
   * A builder for creating immutable map instances, especially {@code public
   * static final} maps ("constant maps"). Example: <pre>   {@code
   *
   *   static final ImmutableMap<String, Integer> WORD_TO_INT =
   *       new ImmutableMap.Builder<String, Integer>()
   *           .put("one", 1)
   *           .put("two", 2)
   *           .put("three", 3)
   *           .build();}</pre>
   *
   * <p>For <i>small</i> immutable maps, the {@code ImmutableMap.of()} methods are
   * even more convenient.
   *
   * <p>Builder instances can be reused - it is safe to call {@link #build}
   * multiple times to build multiple maps in series. Each map is a superset of
   * the maps created before it.
   *
   * @since 2.0
   */
  public static class Builder<K, V> {
    Comparator<? super V> valueComparator;
    ImmutableMapEntry<K, V>[] entries;
    int size;
    boolean entriesUsed;

    /**
     * Creates a new builder. The returned builder is equivalent to the builder
     * generated by {@link ImmutableMap#builder}.
     */
    public Builder() {
      this(ImmutableCollection.Builder.DEFAULT_INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    Builder(int initialCapacity) {
      this.entries = new ImmutableMapEntry[initialCapacity];
      this.size = 0;
      this.entriesUsed = false;
    }

    private void ensureCapacity(int minCapacity) {
      if (minCapacity > entries.length) {
        entries =
            ObjectArrays.arraysCopyOf(
                entries, ImmutableCollection.Builder.expandedCapacity(entries.length, minCapacity));
        entriesUsed = false;
      }
    }

    /**
     * Associates {@code key} with {@code value} in the built map. Duplicate
     * keys are not allowed, and will cause {@link #build} to fail.
     */
    @CanIgnoreReturnValue
    public Builder<K, V> put(K key, V value) {
      ensureCapacity(size + 1);
      ImmutableMapEntry<K, V> entry = entryOf(key, value);
      // don't inline this: we want to fail atomically if key or value is null
      entries[size++] = entry;
      return this;
    }

    /**
     * Adds the given {@code entry} to the map, making it immutable if
     * necessary. Duplicate keys are not allowed, and will cause {@link #build}
     * to fail.
     *
     * @since 11.0
     */
    @CanIgnoreReturnValue
    public Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
      return put(entry.getKey(), entry.getValue());
    }

    /**
     * Associates all of the given map's keys and values in the built map.
     * Duplicate keys are not allowed, and will cause {@link #build} to fail.
     *
     * @throws NullPointerException if any key or value in {@code map} is null
     */
    @CanIgnoreReturnValue
    public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
      return putAll(map.entrySet());
    }

    /**
     * Adds all of the given entries to the built map.  Duplicate keys are not
     * allowed, and will cause {@link #build} to fail.
     *
     * @throws NullPointerException if any key, value, or entry is null
     * @since 19.0
     */
    @CanIgnoreReturnValue
    @Beta
    public Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
      if (entries instanceof Collection) {
        ensureCapacity(size + ((Collection<?>) entries).size());
      }
      for (Entry<? extends K, ? extends V> entry : entries) {
        put(entry);
      }
      return this;
    }

    /**
     * Configures this {@code Builder} to order entries by value according to the specified
     * comparator.
     *
     * <p>The sort order is stable, that is, if two entries have values that compare
     * as equivalent, the entry that was inserted first will be first in the built map's
     * iteration order.
     *
     * @throws IllegalStateException if this method was already called
     * @since 19.0
     */
    @CanIgnoreReturnValue
    @Beta
    public Builder<K, V> orderEntriesByValue(Comparator<? super V> valueComparator) {
      checkState(this.valueComparator == null, "valueComparator was already set");
      this.valueComparator = checkNotNull(valueComparator, "valueComparator");
      return this;
    }

    /*
     * TODO(kevinb): Should build() and the ImmutableBiMap & ImmutableSortedMap
     * versions throw an IllegalStateException instead?
     */

    /**
     * Returns a newly-created immutable map.
     *
     * @throws IllegalArgumentException if duplicate keys were added
     */
    public ImmutableMap<K, V> build() {
      switch (size) {
        case 0:
          return of();
        case 1:
          return of(entries[0].getKey(), entries[0].getValue());
        default:
          /*
           * If entries is full, then this implementation may end up using the entries array
           * directly and writing over the entry objects with non-terminal entries, but this is
           * safe; if this Builder is used further, it will grow the entries array (so it can't
           * affect the original array), and future build() calls will always copy any entry
           * objects that cannot be safely reused.
           */
          if (valueComparator != null) {
            if (entriesUsed) {
              entries = ObjectArrays.arraysCopyOf(entries, size);
            }
            Arrays.sort(
                entries,
                0,
                size,
                Ordering.from(valueComparator).onResultOf(Maps.<V>valueFunction()));
          }
          entriesUsed = size == entries.length;
          return RegularImmutableMap.fromEntryArray(size, entries);
      }
    }
  }

  /**
   * Returns an immutable map containing the same entries as {@code map}. If
   * {@code map} somehow contains entries with duplicate keys (for example, if
   * it is a {@code SortedMap} whose comparator is not <i>consistent with
   * equals</i>), the results of this method are undefined.
   *
   * <p>Despite the method name, this method attempts to avoid actually copying
   * the data when it is safe to do so. The exact circumstances under which a
   * copy will or will not be performed are undocumented and subject to change.
   *
   * @throws NullPointerException if any key or value in {@code map} is null
   */
  public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
    if ((map instanceof ImmutableMap) && !(map instanceof ImmutableSortedMap)) {
      // TODO(lowasser): Make ImmutableMap.copyOf(immutableBiMap) call copyOf()
      // on the ImmutableMap delegate(), rather than the bimap itself

      @SuppressWarnings("unchecked") // safe since map is not writable
      ImmutableMap<K, V> kvMap = (ImmutableMap<K, V>) map;
      if (!kvMap.isPartialView()) {
        return kvMap;
      }
    } else if (map instanceof EnumMap) {
      @SuppressWarnings("unchecked") // safe since map is not writable
      ImmutableMap<K, V> kvMap = (ImmutableMap<K, V>) copyOfEnumMap((EnumMap<?, ?>) map);
      return kvMap;
    }
    return copyOf(map.entrySet());
  }

  /**
   * Returns an immutable map containing the specified entries.  The returned
   * map iterates over entries in the same order as the original iterable.
   *
   * @throws NullPointerException if any key, value, or entry is null
   * @throws IllegalArgumentException if two entries have the same key
   * @since 19.0
   */
  @Beta
  public static <K, V> ImmutableMap<K, V> copyOf(
      Iterable<? extends Entry<? extends K, ? extends V>> entries) {
    @SuppressWarnings("unchecked") // we'll only be using getKey and getValue, which are covariant
    Entry<K, V>[] entryArray = (Entry<K, V>[]) Iterables.toArray(entries, EMPTY_ENTRY_ARRAY);
    switch (entryArray.length) {
      case 0:
        return of();
      case 1:
        Entry<K, V> onlyEntry = entryArray[0];
        return of(onlyEntry.getKey(), onlyEntry.getValue());
      default:
        /*
         * The current implementation will end up using entryArray directly, though it will write
         * over the (arbitrary, potentially mutable) Entry objects actually stored in entryArray.
         */
        return RegularImmutableMap.fromEntries(entryArray);
    }
  }

  private static <K extends Enum<K>, V> ImmutableMap<K, V> copyOfEnumMap(
      EnumMap<K, ? extends V> original) {
    EnumMap<K, V> copy = new EnumMap<K, V>(original);
    for (Map.Entry<?, ?> entry : copy.entrySet()) {
      checkEntryNotNull(entry.getKey(), entry.getValue());
    }
    return ImmutableEnumMap.asImmutable(copy);
  }

  static final Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Entry<?, ?>[0];

  abstract static class IteratorBasedImmutableMap<K, V> extends ImmutableMap<K, V> {
    abstract UnmodifiableIterator<Entry<K, V>> entryIterator();

    @Override
    ImmutableSet<Entry<K, V>> createEntrySet() {
      @WeakOuter
      class EntrySetImpl extends ImmutableMapEntrySet<K, V> {
        @Override
        ImmutableMap<K, V> map() {
          return IteratorBasedImmutableMap.this;
        }

        @Override
        public UnmodifiableIterator<Entry<K, V>> iterator() {
          return entryIterator();
        }
      }
      return new EntrySetImpl();
    }
  }

  ImmutableMap() {}

  /**
   * Guaranteed to throw an exception and leave the map unmodified.
   *
   * @throws UnsupportedOperationException always
   * @deprecated Unsupported operation.
   */
  @CanIgnoreReturnValue
  @Deprecated
  @Override
  public final V put(K k, V v) {
    throw new UnsupportedOperationException();
  }

  /**
   * Guaranteed to throw an exception and leave the map unmodified.
   *
   * @throws UnsupportedOperationException always
   * @deprecated Unsupported operation.
   */
  @CanIgnoreReturnValue
  @Deprecated
  @Override
  public final V remove(Object o) {
    throw new UnsupportedOperationException();
  }

  /**
   * Guaranteed to throw an exception and leave the map unmodified.
   *
   * @throws UnsupportedOperationException always
   * @deprecated Unsupported operation.
   */
  @Deprecated
  @Override
  public final void putAll(Map<? extends K, ? extends V> map) {
    throw new UnsupportedOperationException();
  }

  /**
   * Guaranteed to throw an exception and leave the map unmodified.
   *
   * @throws UnsupportedOperationException always
   * @deprecated Unsupported operation.
   */
  @Deprecated
  @Override
  public final void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean containsKey(@Nullable Object key) {
    return get(key) != null;
  }

  @Override
  public boolean containsValue(@Nullable Object value) {
    return values().contains(value);
  }

  // Overriding to mark it Nullable
  @Override
  public abstract V get(@Nullable Object key);

  @LazyInit
  private transient ImmutableSet<Entry<K, V>> entrySet;

  /**
   * Returns an immutable set of the mappings in this map. The entries are in
   * the same order as the parameters used to build this map.
   */
  @Override
  public ImmutableSet<Entry<K, V>> entrySet() {
    ImmutableSet<Entry<K, V>> result = entrySet;
    return (result == null) ? entrySet = createEntrySet() : result;
  }

  abstract ImmutableSet<Entry<K, V>> createEntrySet();

  @LazyInit
  private transient ImmutableSet<K> keySet;

  /**
   * Returns an immutable set of the keys in this map. These keys are in
   * the same order as the parameters used to build this map.
   */
  @Override
  public ImmutableSet<K> keySet() {
    ImmutableSet<K> result = keySet;
    return (result == null) ? keySet = createKeySet() : result;
  }

  ImmutableSet<K> createKeySet() {
    return isEmpty() ? ImmutableSet.<K>of() : new ImmutableMapKeySet<K, V>(this);
  }

  UnmodifiableIterator<K> keyIterator() {
    final UnmodifiableIterator<Entry<K, V>> entryIterator = entrySet().iterator();
    return new UnmodifiableIterator<K>() {
      @Override
      public boolean hasNext() {
        return entryIterator.hasNext();
      }

      @Override
      public K next() {
        return entryIterator.next().getKey();
      }
    };
  }

  @LazyInit
  private transient ImmutableCollection<V> values;

  /**
   * Returns an immutable collection of the values in this map. The values are
   * in the same order as the parameters used to build this map.
   */
  @Override
  public ImmutableCollection<V> values() {
    ImmutableCollection<V> result = values;
    return (result == null) ? values = createValues() : result;
  }

  ImmutableCollection<V> createValues() {
    return new ImmutableMapValues<K, V>(this);
  }

  // cached so that this.multimapView().inverse() only computes inverse once
  @LazyInit
  private transient ImmutableSetMultimap<K, V> multimapView;

  /**
   * Returns a multimap view of the map.
   *
   * @since 14.0
   */
  public ImmutableSetMultimap<K, V> asMultimap() {
    if (isEmpty()) {
      return ImmutableSetMultimap.of();
    }
    ImmutableSetMultimap<K, V> result = multimapView;
    return (result == null)
        ? (multimapView =
            new ImmutableSetMultimap<K, V>(new MapViewOfValuesAsSingletonSets(), size(), null))
        : result;
  }

  @WeakOuter
  private final class MapViewOfValuesAsSingletonSets
      extends IteratorBasedImmutableMap<K, ImmutableSet<V>> {

    @Override
    public int size() {
      return ImmutableMap.this.size();
    }

    @Override
    public ImmutableSet<K> keySet() {
      return ImmutableMap.this.keySet();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
      return ImmutableMap.this.containsKey(key);
    }

    @Override
    public ImmutableSet<V> get(@Nullable Object key) {
      V outerValue = ImmutableMap.this.get(key);
      return (outerValue == null) ? null : ImmutableSet.of(outerValue);
    }

    @Override
    boolean isPartialView() {
      return ImmutableMap.this.isPartialView();
    }

    @Override
    public int hashCode() {
      // ImmutableSet.of(value).hashCode() == value.hashCode(), so the hashes are the same
      return ImmutableMap.this.hashCode();
    }

    @Override
    boolean isHashCodeFast() {
      return ImmutableMap.this.isHashCodeFast();
    }

    @Override
    UnmodifiableIterator<Entry<K, ImmutableSet<V>>> entryIterator() {
      final Iterator<Entry<K, V>> backingIterator = ImmutableMap.this.entrySet().iterator();
      return new UnmodifiableIterator<Entry<K, ImmutableSet<V>>>() {
        @Override
        public boolean hasNext() {
          return backingIterator.hasNext();
        }

        @Override
        public Entry<K, ImmutableSet<V>> next() {
          final Entry<K, V> backingEntry = backingIterator.next();
          return new AbstractMapEntry<K, ImmutableSet<V>>() {
            @Override
            public K getKey() {
              return backingEntry.getKey();
            }

            @Override
            public ImmutableSet<V> getValue() {
              return ImmutableSet.of(backingEntry.getValue());
            }
          };
        }
      };
    }
  }

  @Override
  public boolean equals(@Nullable Object object) {
    return Maps.equalsImpl(this, object);
  }

  abstract boolean isPartialView();

  @Override
  public int hashCode() {
    return Sets.hashCodeImpl(entrySet());
  }

  boolean isHashCodeFast() {
    return false;
  }

  @Override
  public String toString() {
    return Maps.toStringImpl(this);
  }

  /**
   * Serialized type for all ImmutableMap instances. It captures the logical
   * contents and they are reconstructed using public factory methods. This
   * ensures that the implementation types remain as implementation details.
   */
  static class SerializedForm implements Serializable {
    private final Object[] keys;
    private final Object[] values;

    SerializedForm(ImmutableMap<?, ?> map) {
      keys = new Object[map.size()];
      values = new Object[map.size()];
      int i = 0;
      for (Entry<?, ?> entry : map.entrySet()) {
        keys[i] = entry.getKey();
        values[i] = entry.getValue();
        i++;
      }
    }

    Object readResolve() {
      Builder<Object, Object> builder = new Builder<Object, Object>(keys.length);
      return createMap(builder);
    }

    Object createMap(Builder<Object, Object> builder) {
      for (int i = 0; i < keys.length; i++) {
        builder.put(keys[i], values[i]);
      }
      return builder.build();
    }

    private static final long serialVersionUID = 0;
  }

  Object writeReplace() {
    return new SerializedForm(this);
  }
}

```





如此处理， 返回的结果则便是树形结构。



##### 懒加载树形结构。

- controller

```java
/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public R<List<EmpRuleCatVO>> lazyList(Long parentId, @ApiIgnore @RequestParam Map<String, Object> empRuleCat) {
		List<EmpRuleCatVO> list = empRuleCatService.lazyList(parentId, empRuleCat);
		return R.data(EmpRuleCatWrapper.build().listNodeLazyVo(list));
	}
```

- service

```java
	@Override
	public List<EmpRuleCatVO> lazyList(Long parentId, Map<String, Object> param) {
		if (Func.isEmpty(Func.toStr(param.get("parentId")))) {
			parentId = null;
		}
		return baseMapper.lazyList(parentId, param);

	}

```

mapper.xml

```xml
 <select id="lazyList" resultMap="empRuleCatVOResultMap">
        select
            rulecat.*,
            (
                SELECT
                     CASE WHEN count( 1 ) > 0 THEN 1 ELSE 0 END
                FROM
                    t_emp_rule_cat
                 WHERE
                     parent_id = rulecat.id AND is_deleted = 0
            ) AS "has_children"
        FROM
            t_emp_rule_cat rulecat
        WHERE rulecat.is_deleted = 0
        <if test="param1!=null">
            and rulecat.parent_id = #{param1}
        </if>
        <if test="param2.title!=null and param2.title!=''">
            and rulecat.title like concat(concat('%', #{param2.title}), '%')
        </if>
        ORDER BY rulecat.sort
    </select>
```



所用的wrpper包装类

```wrapper

package org.springblade.modules.punishment.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.punishment.entity.EmpRuleCat;
import org.springblade.modules.punishment.vo.EmpRuleCatVO;

import java.util.List;
import java.util.Objects;

/**
 * 处罚规则类目包装类,返回视图层所需的字段
 *
 * @author BladeX
 * @since 2021-05-18
 */
public class EmpRuleCatWrapper extends BaseEntityWrapper<EmpRuleCat, EmpRuleCatVO>  {

	public static EmpRuleCatWrapper build() {
		return new EmpRuleCatWrapper();
 	}

	@Override
	public EmpRuleCatVO entityVO(EmpRuleCat empRuleCat) {
		EmpRuleCatVO empRuleCatVO = Objects.requireNonNull(BeanUtil.copy(empRuleCat, EmpRuleCatVO.class));

		//User createUser = UserCache.getUser(empRuleCat.getCreateUser());
		//User updateUser = UserCache.getUser(empRuleCat.getUpdateUser());
		//empRuleCatVO.setCreateUserName(createUser.getName());
		//empRuleCatVO.setUpdateUserName(updateUser.getName());

		return empRuleCatVO;
	}

	public List<EmpRuleCatVO> listNodeLazyVo(List<EmpRuleCatVO> list) {
		return ForestNodeMerger.merge(list);
	}


}

```



则可返回懒加载的树形结构。



