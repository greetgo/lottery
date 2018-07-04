package kz.greetgo.sandbox.controller.model.help;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LotteryHelp {
  public String title = "Rest-сервисы лотереи";
  public final List<LotteryServiceHelp> serviceList = new ArrayList<>();

  {
    LotteryServiceHelp s = new LotteryServiceHelp();
    s.title = "Продажа билета";
    s.href = UrlPrefix.get() + "/lottery/sell_ticket";
    serviceList.add(s);
  }
}
