package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;
import kz.greetgo.sandbox.controller.register.LotteryRegister;

import java.util.Date;

@Bean
public class LotteryRegisterImpl implements LotteryRegister {
  @Override
  public String ok() {
    return "OK 2";
  }

  @Override
  public SZWagerAck sellTicket(SZWagerReq req) {

    System.out.println("sellTicket " + req);

    SZWagerAck ret = new SZWagerAck();
    ret.isFreeTicket = true;
    ret.ticketNo = 123;
    ret.transactionDateBetNet = new Date();
    return ret;
  }
}
