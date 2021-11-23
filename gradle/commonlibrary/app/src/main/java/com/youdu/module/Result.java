package com.youdu.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * 创建时间:  2017/06/12 17:01 <br>
 * 作者:  renzhiqiang <br>
 * 描述:
 */
public class Result<T> implements Serializable {

  @SerializedName(value = "errno") @Expose public int errno;
  @SerializedName(value = "error") @Expose public String error;
  @Expose public T data;

  public Result() {
  }
}
