(ns pages.missionary
  (:require [components :refer [page]]
            [borkdude.html :refer [html]]
            [highlight :refer [highlight-clj]]))

(defn missionary []
  (page
   (html
    [:<>
     [:h1 "FRP in Clojure with Missionary"]
     [:h3 "August 2025"]
     [:p "I've been working in Clojure on and off for nearly a decade now. In
that time, I've spent thousands of hours working in other languages
professionally: Python, Ruby, TypeScript / Flow / JavaScript, Kotlin, and Java.
I've experimented with many more, to varying degrees: Zig, Janet, Prolog, and
Factor, to name a few. At the end of the day, though, I always find myself
coming back to Clojure."]
     [:p "Though it's hard to pin down exactly why I find myself so drawn by
the language, it seems to have something to do with the relatively high incidence
of exposure to new, mind-expanding ideas that I've experienced while working
in Clojure and its ecosystem."]
     [:p "For one reason or another, Clojure seems to attract a high density of
smart people who are experimenting with fairly radical ways of solving hard
problems. Something about the combination of the language's simple macro system,
data orientation, reasonable performance, and practical approach to
functional programming make it a great foundation for building more complicated
experimental language constructs."]
     [:p "It is via Clojure that I first came to be exposed to datalog, for example,
which helped me begin to be able to articulate why I struggled with SQL's
relativel lack of expressivity. From there, I discovered prolog and the world of
\"search programming\", which gave me an entirely different way to frame
certain problems."]
     [:p "Similarly, I first learned of Datomic through Clojure, which blew my
mind the first time I encountered it. The idea of treating your entire database
as a single, immutable value would never have occurred to me on my own;
once I encountered it, however, the alternative seemed patently absurd."]
     [:p "The Clojure library " [:span.inline-code "meander"] " showed me that term rewriting systems
could be used for data transformation. (Did you know that term rewriting systems
have the full power of turing machines?) The folks over at Hyperfiddle
have proven that you can effectively abstract away the network connection that
sits between a client and a server, and they have credited Clojure for that
accomplishment. Similarly, my experience so far with Rama, from Red Planet Labs,
have made it hard for me to go back to \"traditional backend programming\".
(I could write a whole series of posts on that topic. Reach out if you'd find
that interesting.) They, too, credit Clojure for their progress."]
     [:p "In this post, I want to use the FRP library " [:span.inline-code "missionary"] " to give an
example of what it means to embed an entirely new concept within an existing
programming language. On the one hand, missionary is simply a library. On the
other hand, it differs significantly from the kind of library that you might
find in other programming languages because it introduces its own syntax and
semantics that integrate seamlessly with the host language, Clojure. (This is
the old \"promise of the DSL\" that lisps are famous for.)"]
     [:p "First, though: what is missionary? At a high level, missionary
seeks to make it easier to write correct asynchronous (and concurrent) programs
correctly, efficently (meaning that it makes effective use of available CPU
resources), and in a manner that will feel comfortable to functional programmers.
To accomplish that, missionary draws inspiration from a number of interesting
programming ideas and diciplines:"]
     [:ul
      [:li "Functional Reactive Programming, or FRP"]
      [:li "Process supervision"]
      [:li "Structured concurrency"]
      [:li "Ambiguous programming"]
      [:li "TKTK"]]
     [:p "To start our journey, I want to show you the equivelant of a \"hello, world!\"
program, written with missionary. This program captures the current time, in milliseconds;
it sleeps for one second; and then it captures the current time again."]
     (highlight-clj
      (require '[missionary.core :as m])

      (def run
        (m/ap (tap> [:a (System/currentTimeMillis)])
              (m/? (m/sleep 1000))
              (tap> [:b (System/currentTimeMillis)])))

      (m/? (m/reduce {} nil run)))
     [:p "(In these examples, you'll note my use of the " [:span.inline-code "(tap> ...)"] " function. This
function appends the value of its argument to a list. After the program runs,
I print the contents of that list to the \"tap contents:\" box below the code.)"]

     [:p "TKTK Note, however, that " [:span.inline-code "run"] " doesn't actually execute until we feed it
to the " [:span.inline-code "reduce"] " function. It's too early to explain how " [:span.inline-code "reduce"] " has anything
to do with running a program – we'll have to build some other intuition first.
For now, when you see " [:span.inline-code "reduce"] ", just think \"run a sequence of operations.\""]

     [:p "You might be thinking: "
      [:em "I could write this code just as easily without missionary.
What's the point? "]
      "Great question. We should go ahead and do that. Let's write this program once
without missionary, and then again with missionary but with a bit of additional
debugging thrown in:"]

     (highlight-clj
      (defn log
        "Emit `v` to `tap>`, capturing the current time and active thread along the way."
        [v]
        (tap> [v (System/currentTimeMillis) (.getName (Thread/currentThread))]))
      (defn run-native []
        (log :native-a)
        (Thread/sleep 1000)
        (log :native-b))

      (tap> (-> (run-native)
                (time)
                (with-out-str)))

      (def run-missionary
        (m/ap (log :missionary-a)
              (m/? (m/sleep 1000))
              (log :missionary-b)))

      (tap> (-> (m/reduce {} nil run-missionary)
                (m/?)
                (time)
                (with-out-str))))
     ;; TODO: Fix bug where second log doesn't
     ;; always print!

     [:p "A few things might jump out immediately."]
     [:p "First, both "
      [:span.inline-code "run-native"] " and "
      [:span.inline-code "run-missionary"]
      " block during their evaluation.  Notice that the runtime
     for each is approximately 1000ms."]
     [:p "Second, "
      [:span.inline-code "run-native"]
      " evaluates the entire expression on the repl's main thread.i By contrast, "
      [:span.inline-code "run-missionary"]
      " seems to move the
     form after the sleep expression to some kind of background thread. What's
     that about?"]  [:p "Oh, by the way. At the beginning of this post, I made
     it clear that Clojure has had a major impact on the way I think about
     programming, in large part because the language has facilities that make it
     particularly attractive for experimentation. While I believe that to be
     true, I know that Clojure is hardly alone in its expressiveness. If you are
     a Haskeller, an OCameler, a Common Lisper, an APLer, or even a C
     programmer, and you're thinking to yourself \"Clojure isn't special in this
     regard\", well, I'm sure you're right! Shoot me an email with your
     thoughts, I'd love to learn more about why your language of choice is
     unique in this regard. I'll update this post with your contribution."]
     [:h2 "References"]
     [:ul
      [:li "TODO flow spec"]
      [:li "TODO task spec"]
      [:li "TODO process supervision"]
      [:li
       [:a {:href "https://github.com/leonoel/missionary"} "Missionary"]]
      [:li [:a {:href "https://github.com/hyperfiddle/electric"} "Electric Clojure"]]
      [:li [:a {:href "https://github.com/noprompt/meander"} "Meander"]]
      [:li [:a {:href "https://jimmyhmiller.com/meander-rewriting"}
            "Introduction to Term Rewriting with Meander"]]]])))
