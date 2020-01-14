import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object VoteSimulation {
    def randomString(length: Int) = scala.util.Random.alphanumeric.filter(_.isLetter).take(length).mkString
    def randomInt() = scala.util.Random.nextInt()
}

class VoteSimulation extends Simulation {

    var randomId = Iterator.continually(Map("voterId" -> VoteSimulation.randomInt()))

    val httpProtocol = http
      .baseUrl("http://localhost:8501")
      .inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")

    val Scenario_CreatE = scenario("Cast votes scenario")
      .feed(randomId)
      .exec(http("Cast vote")
        .post("/polls/1/votes")
        .header(HttpHeaderNames.ContentType, HttpHeaderValues.ApplicationJson)
        .header(HttpHeaderNames.Accept, HttpHeaderValues.ApplicationJson)
        .body(StringBody("""{
          "pollOption": "Sim",
          "voterId": ${voterId}
        }"""")).asJson
      )

    setUp(
        Scenario_CreatE.inject(
            nothingFor(5 seconds),
            atOnceUsers(5),
            rampUsers(10) during (10 seconds),
            rampUsersPerSec(1) to 200 during (2 minutes) randomized
        )
    ).protocols(httpProtocol)
}

