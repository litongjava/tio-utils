package com.litongjava.tio.utils.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import com.litongjava.model.func.Converter;
import com.litongjava.model.page.Page;
import com.litongjava.tio.utils.lock.SetWithLock;

/**
 * @author tanyaowu
 * 2017年5月10日 下午1:14:15
 */
public class PageUtils {
  @SuppressWarnings("unchecked")
  public static <T> Page<T> fromList(List<T> list, int pageNumber, int pageSize) {
    return fromList((List<Object>) list, pageNumber, pageSize, (Converter<T>) (null));
  }

  @SuppressWarnings("unchecked")
  public static <T> Page<T> fromList(List<?> list, int pageNumber, int pageSize, Converter<T> converter) {
    if (list == null) {
      return null;
    }

    Page<Object> page = pre(list, pageNumber, pageSize);

    List<Object> pageData = page.getList();
    if (pageData == null) {
      return (Page<T>) page;
    }

    int startIndex = Math.min((page.getPageNumber() - 1) * page.getPageSize(), list.size());
    int endIndex = Math.min(page.getPageNumber() * page.getPageSize(), list.size());

    for (int i = startIndex; i < endIndex; i++) {
      if (converter != null) {
        pageData.add(converter.convert(list.get(i)));
      } else {
        pageData.add(list.get(i));
      }

    }
    page.setList(pageData);
    return (Page<T>) page;
  }

  @SuppressWarnings("unchecked")
  public static <T> Page<T> fromSet(Set<T> set, int pageNumber, int pageSize) {
    return fromSet((Set<Object>) set, pageNumber, pageSize, (Converter<T>) null);
  }

  @SuppressWarnings("unchecked")
  public static <T> Page<T> fromSet(Set<?> set, int pageNumber, int pageSize, Converter<T> converter) {
    if (set == null) {
      return null;
    }

    Page<Object> page = pre(set, pageNumber, pageSize);

    List<Object> pageData = page.getList();
    if (pageData == null) {
      return (Page<T>) page;
    }

    int startIndex = Math.min((page.getPageNumber() - 1) * page.getPageSize(), set.size());
    int endIndex = Math.min(page.getPageNumber() * page.getPageSize(), set.size());

    int i = 0;
    for (Object t : set) {
      if (i >= endIndex) {
        break;
      }
      if (i < startIndex) {
        i++;
        continue;
      }

      if (converter != null) {
        pageData.add(converter.convert(t));
      } else {
        pageData.add(t);
      }

      i++;
      continue;
    }
    page.setList(pageData);
    return (Page<T>) page;
  }

  @SuppressWarnings("unchecked")
  public static <T> Page<T> fromSetWithLock(SetWithLock<T> setWithLock, int pageNumber, int pageSize) {
    return fromSetWithLock((SetWithLock<Object>) setWithLock, pageNumber, pageSize, (Converter<T>) null);
  }

  public static <T> Page<T> fromSetWithLock(SetWithLock<?> setWithLock, int pageNumber, int pageSize, Converter<T> converter) {
    if (setWithLock == null) {
      return null;
    }
    Lock lock = setWithLock.readLock();
    lock.lock();
    try {
      @SuppressWarnings("unchecked")
      Set<Object> set = (Set<Object>) setWithLock.getObj();
      return fromSet(set, pageNumber, pageSize, converter);
    } finally {
      lock.unlock();
    }
  }

  private static Page<Object> pre(java.util.Collection<?> allList, int pageNumber, int pageSize) {
    if (allList == null) {
      return new Page<>(null, pageNumber, pageSize, 0);
    }

    pageSize = processPageSize(pageSize);
    pageNumber = processpageNumber(pageNumber);

    int recordCount = allList.size();
    if (pageSize > recordCount) {
      pageSize = recordCount;
    }

    List<Object> pageData = new ArrayList<>(pageSize);
    Page<Object> ret = new Page<Object>(pageData, pageNumber, pageSize, recordCount);
    return ret;
  }

  private static int processpageNumber(int pageNumber) {
    return pageNumber <= 0 ? 1 : pageNumber;
  }

  private static int processPageSize(int pageSize) {
    return pageSize <= 0 ? Integer.MAX_VALUE : pageSize;
  }

}
