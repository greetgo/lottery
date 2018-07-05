package kz.greetgo.sandbox.controller.model;

import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class SZWagerReqTest {
  @Test
  public void serialize_deserialize() {
    SZWagerReq a1 = new SZWagerReq();
    a1.TVN = RND.rnd.nextInt();
    a1.operatorIndex = RND.rnd.nextInt();
    a1.wagerBetData.onlineBetModeNo = RND.rnd.nextInt();
    a1.wagerBetData.onlineGameNo = RND.rnd.nextInt();

    //
    //
    byte[] array = a1.serialize();
    SZWagerReq a2 = SZWagerReq.deserialize(array);
    //
    //

    assertThat(a2).isNotNull();
    assertThat(a2.TVN).isEqualTo(a1.TVN);
    assertThat(a2.operatorIndex).isEqualTo(a1.operatorIndex);
    assertThat(a2.wagerBetData.onlineBetModeNo).isEqualTo(a1.wagerBetData.onlineBetModeNo);
    assertThat(a2.wagerBetData.onlineGameNo).isEqualTo(a1.wagerBetData.onlineGameNo);
  }
}