package kz.greetgo.sandbox.stand.bean_containers;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.controller.controller.BeanConfigControllers;
import kz.greetgo.sandbox.db.beans.all.BeanConfigAll;
import kz.greetgo.sandbox.stand.beans.BeanConfigStand;

@BeanConfig
@Include({BeanConfigStand.class, BeanConfigAll.class, BeanConfigControllers.class})
public class BeanConfigForStandBeanContainer {}
