package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.MethodFilter;
import kz.greetgo.mvc.annotations.RequestInput;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.core.RequestMethod;
import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;
import kz.greetgo.sandbox.controller.model.help.HtmlText;
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
  @MethodFilter(RequestMethod.GET)
  public JsText jQuery() {
    return helpRegister.get().jQuery();
  }

  @ToJson
  @NoSecurity
  @Mapping({"/", ""})
  @MethodFilter(RequestMethod.GET)
  public Object root() {
    return helpRegister.get().lottery();
  }

  @ToJson
  @NoSecurity
  @Mapping("/sell_ticket")
  @MethodFilter(RequestMethod.GET)
  public HtmlText sellTicketGET(@RequestInput @Json SZWagerReq req) {
    return helpRegister.get().lotterySellTicket();
  }

  @ToJson
  @NoSecurity
  @Mapping("/sell_ticket")
  @MethodFilter(RequestMethod.POST)
  public SZWagerAck sellTicket(@RequestInput @Json SZWagerReq req) {
    return lotteryRegister.get().sellTicket(req);
  }
}
