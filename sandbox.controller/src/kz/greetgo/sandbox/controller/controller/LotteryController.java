package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.RequestInput;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.sandbox.controller.model.SZWagerReq;
import kz.greetgo.sandbox.controller.model.help.JsText;
import kz.greetgo.sandbox.controller.register.HelpRegister;
import kz.greetgo.sandbox.controller.register.LotteryRegister;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

@Bean
@Mapping("/lottery")
public class LotteryController implements Controller {

  public BeanGetter<LotteryRegister> lotteryRegister;
  public BeanGetter<HelpRegister> helpRegister;

  @ToJson
  @NoSecurity
  @Mapping("/jquery.js")
  public JsText jQuery() {
    return helpRegister.get().jQuery();
  }

  @ToJson
  @NoSecurity
  @Mapping({"/", ""})
  public Object root() {
    return helpRegister.get().lottery();
  }

  @ToJson
  @NoSecurity
  @Mapping("/sell_ticket")
  public Object sellTicket(@RequestInput @Json SZWagerReq req) {
    if (req != null && req.filled()) {
      return lotteryRegister.get().sellTicket(req);
    }
    return helpRegister.get().lotterySellTicket();
  }
}
