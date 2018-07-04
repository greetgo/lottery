package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.mvc.annotations.AsIs;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

@Bean
@Mapping("/lottery")
public class LotteryController implements Controller {

  @AsIs
  @NoSecurity
  @Mapping("/test")
  public String test() {
    return "OK";
  }


}
