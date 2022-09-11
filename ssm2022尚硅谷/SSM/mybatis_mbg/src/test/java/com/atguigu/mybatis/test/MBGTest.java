package com.atguigu.mybatis.test;

import com.atguigu.mybatis.mapper.EmpMapper;
import com.atguigu.mybatis.pojo.Emp;
import com.atguigu.mybatis.pojo.EmpExample;
import com.atguigu.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author maomao
 * @create 2022-07-22 17:05
 */
public class MBGTest {

    @Test
    public void testMBG(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);

        //根据id查询数据
//        Emp emp = mapper.selectByPrimaryKey(1);
//        System.out.println(emp);

        //查询所有数据
//        List<Emp> emps = mapper.selectByExample(null);
//        emps.forEach(System.out::println);

        //根据条件查询数据
//        EmpExample empExample = new EmpExample();
//        empExample.createCriteria().andEmpNameEqualTo("张三").andAgeGreaterThanOrEqualTo(20);
//        empExample.or().andGenderEqualTo("男");
//        List<Emp> emps = mapper.selectByExample(empExample);
//        emps.forEach(System.out::println);

        //测试普通修改功能
//        Emp emp = new Emp(1, "小黑", null, "女");
//        mapper.updateByPrimaryKey(emp);

        //测试选择性修改
        Emp emp = new Emp(1, "小黑", null, "女");
        mapper.updateByPrimaryKeySelective(emp);

        sqlSession.close();
    }
}
