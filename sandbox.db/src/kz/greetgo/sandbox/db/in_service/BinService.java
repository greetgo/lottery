package kz.greetgo.sandbox.db.in_service;

import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;

public interface BinService {
  SZWagerAck call(SZWagerReq req);
}
