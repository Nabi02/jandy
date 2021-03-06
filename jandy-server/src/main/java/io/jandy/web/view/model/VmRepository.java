package io.jandy.web.view.model;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.eclipse.egit.github.core.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author JCooky
 * @since 2015-07-01
 */
public class VmRepository extends Repository {
  private boolean imported = false;

  public VmRepository() {
  }

  public VmRepository(Repository repository) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    for (Field field : FieldUtils.getAllFields(Repository.class)) {
      Method method = MethodUtils.getAccessibleMethod(Repository.class, (field.getType().equals(boolean.class) ? "is" : "get") + StringUtils.capitalize(field.getName()));
      if (method != null) {
        Object value = method.invoke(repository);
        method = MethodUtils.getAccessibleMethod(Repository.class, "set" + StringUtils.capitalize(field.getName()), field.getType());
        method.invoke(this, value);
      }
    }
  }

  public boolean isImported() {
    return imported;
  }

  public void setImported(boolean imported) {
    this.imported = imported;
  }

}
