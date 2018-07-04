package kz.greetgo.sandbox.db.register_impl.help;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.help.HtmlText;
import kz.greetgo.sandbox.controller.model.help.JsText;
import kz.greetgo.sandbox.controller.model.help.UrlPrefix;
import kz.greetgo.sandbox.controller.register.HelpRegister;

import static kz.greetgo.util.ServerUtil.streamToStr;

@Bean
public class HelpRegisterImpl implements HelpRegister {

  @Override
  public JsText jQuery() {
    return () -> getText("jquery-3.3.1.min.js");
  }

  @Override
  public HtmlText lottery() {
    return () -> getText("lottery.html")
      .replaceAll("URL_PREFIX", UrlPrefix.get());
  }

  @Override
  public HtmlText lotterySellTicket() {
    return () -> getText("lotterySellTicket.html")
      .replaceAll("URL_PREFIX", UrlPrefix.get());
  }

  private String getText(String s) {
    return streamToStr(getClass().getResourceAsStream(s));
  }
}
