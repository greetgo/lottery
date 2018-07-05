package kz.greetgo.sandbox.controller.model;

import kz.greetgo.util.RND;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class SZWagerAckTest {

  @DataProvider
  public Object[][] yesNo() {
    return new Object[][]{{true}, {false}};
  }

  @Test(dataProvider = "yesNo")
  public void serialize_deserialize(boolean dateIsNull) {
    SZWagerAck a1 = new SZWagerAck();
    a1.transactionDateBetNet = dateIsNull ? null : RND.dateYears(-1000, 1);
    a1.ticketNo = RND.rnd.nextLong();
    a1.isFreeTicket = RND.bool();

    byte[] arr = a1.serialize();
    SZWagerAck a2 = SZWagerAck.deserialize(arr);

    assertThat(a2).isNotNull();
    assertThat(a2.isFreeTicket).isEqualTo(a1.isFreeTicket);
    assertThat(a2.ticketNo).isEqualTo(a1.ticketNo);
    assertThat(a2.transactionDateBetNet).isEqualTo(a1.transactionDateBetNet);
  }
}