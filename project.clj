(defproject antonov "release.8.71"
  :description "OTA Standard SOAP server"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[bcd/otatree "1.4.0"]
                 [cheshire "5.10.1"]
                 [clj-dbcp "0.9.0"]
                 [clj-unidecode "1.0.2"]
                 [clojure-humanize "0.2.2"]
                 [com.amazonaws/aws-java-sdk "1.12.137"]
                 [com.datadoghq/dd-trace-api "0.31.0"]
                 [com.draines/postal "2.0.5"]
                 [com.fzakaria/slf4j-timbre "0.3.21"]
                 [com.taoensso/carmine "3.1.0"]
                 [com.taoensso/timbre "5.1.2"]
                 [com.taoensso/tufte "2.2.0"]
                 [funcool/cats "2.4.1"]
                 [io.opentracing/opentracing-util "0.33.0"]
                 [http-kit "2.5.3"]
                 [me.raynes/fs "1.4.6"]
                 [migratus "1.3.5"]
                 [mount "0.1.16"]
                 [nrepl "0.9.0"]
                 [org.bovinegenius/exploding-fish "0.3.6"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/java.data "1.0.95"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.clojure/tools.cli "1.0.206"]
                 [org.clojure/tools.namespace "1.2.0"]
                 [org.postgresql/postgresql "42.3.1"]
                 [pandect "1.0.2"]
                 [raven-clj "1.6.0"]
                 [ses-mailer "0.0.4"]
                 [yesql "0.5.3"]
                 [yogthos/config "1.1.9"]]
  :main ^:skip-aot antonov.core
  :target-path "target/%s"
  :repositories [["private" {:url "s3://jvm-maven-repo/releases/"}]]
  :plugins [[com.pupeno/jar-copier "0.4.0"]
            [lein-shell "0.5.0"]
            [lein-test-out "0.3.1"]
            [migratus-lein "0.4.7"]
            [s3-wagon-private "1.1.2"]]
  :resource-paths ["resources"]
  :migratus {:store :database
             :db ~(get (System/getenv) "DATABASE_URL")}
  :profiles {:dev {:source-paths ["dev"]
                   :plugins [[lein-ancient "0.6.15"]
                             [lein-bikeshed "0.4.1"]
                             [lein-cljfmt "0.5.6"]
                             [lein-cloverage "1.0.13"]
                             [lein-kibit "0.1.3"]
                             [lein-nvd "0.5.1"]
                             [jonase/eastwood "0.2.3"]]}
             :uberjar {:aot :all}}
  :jvm-opts ["-Dhttps.protocols=TLSv1.2"
             "-Djdk.tls.client.protocols=TLSv1.2"]
  :java-agents [[com.datadoghq/dd-java-agent "0.31.0"]]
  :jar-copier {:java-agents true
               :destination "target/agents"}
  :prep-tasks ["javac" "compile" "jar-copier"]
  :uberjar-name "antonov-standalone.jar"
  :aliases {"joker" ["shell" "find" "src/antonov" "test/antonov"
                     "-name" "[^.]*.clj" "-exec" "joker" "--lint" "{}" ";"]
            "lint" ["do" ["joker"] ["kibit"] ["eastwood"] ["bikeshed"]
                    ["cljfmt" "check"]]
            "coverage" [["cloverage"]]
            "test-all" ["do" ["lint"] ["coverage"]]})