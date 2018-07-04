package kz.greetgo.sandbox.db.test.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.db.beans.all.BeanConfigAll;
import kz.greetgo.sandbox.db.test.beans.BeanConfigTestBeans;
import kz.greetgo.sandbox.db.test.dao.postgres.BeanConfigTestDao;

@BeanConfig
@Include({BeanConfigTestDao.class, BeanConfigTestBeans.class, BeanConfigAll.class})
public class BeanConfigTests {}
