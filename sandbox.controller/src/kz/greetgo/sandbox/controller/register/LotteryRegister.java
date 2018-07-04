package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;

public interface LotteryRegister {
  String ok();

  SZWagerAck sellTicket(SZWagerReq req);
}
