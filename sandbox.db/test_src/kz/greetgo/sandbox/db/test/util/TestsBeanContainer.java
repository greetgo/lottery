package kz.greetgo.sandbox.db.test.util;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.db.test.beans._develop_.DbWorker;

@Include(BeanConfigTests.class)
public interface TestsBeanContainer extends BeanContainer {
  DbWorker dbWorker();
}
