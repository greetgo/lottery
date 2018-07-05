package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;
import kz.greetgo.sandbox.controller.register.LotteryRegister;
import kz.greetgo.sandbox.db.in_service.BinService;

@Bean
public class LotteryRegisterImpl implements LotteryRegister {
  @Override
  public String ok() {
    return "OK 2";
  }

  public BeanGetter<BinService> binService;

  @Override
  public SZWagerAck sellTicket(SZWagerReq req) {
    return binService.get().call(req);
  }
}
