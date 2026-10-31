package telegramium.bots

import org.scalacheck.Gen
import org.scalatest.funsuite.AnyFunSuite

// PROTOTYPE measuring spec (scratch only)
class GensMeasureSpec extends AnyFunSuite {
  private def time[A](f: => A): (A, Long) = { val t = System.nanoTime(); val r = f; (r, (System.nanoTime() - t) / 1000000) }

  test("sample size by budget") {
    for (b <- 1 to 5; name <- List("Message", "ChatFullInfo", "Update", "SendMessageReq")) {
      val g = Gens.all.toMap.apply(name)
      val (sizes, ms) = time(List.fill(100)(Gen.resize(b, g).sample.get.toString.length))
      println(f"budget=$b%d $name%-16s meanChars=${sizes.sum / sizes.size}%8d maxChars=${sizes.max}%9d ${ms}%5d ms/100")
    }
  }

  test("all gens, 100 samples each at Budget") {
    val (res, ms) = time(Gens.all.map { case (n, g) =>
      val (xs, t) = time(List.fill(100)(Gen.resize(GenPrims.Budget, g).sample.get.toString.length))
      (n, xs.sum / xs.size, xs.max, t)
    })
    println(s"TOTAL ${res.size} gens x 100 samples: $ms ms")
    println("slowest: " + res.sortBy(-_._4).take(5).map(r => s"${r._1}=${r._4}ms").mkString(", "))
    println("biggest: " + res.sortBy(-_._3).take(5).map(r => s"${r._1}=maxChars ${r._3} (mean ${r._2})").mkString(", "))
    val reqs = res.filter(_._1.endsWith("Req"))
    println(s"REQS ${reqs.size}: ${reqs.map(_._4).sum} ms total; biggest=${reqs.maxBy(_._3)}")
  }
}
