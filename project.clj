(defproject globe "0.1.0-SNAPSHOT"
  :description "Minecraft world file wrangler"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-beta2"]
                 [clojurewerkz/buffy "1.0.1"]]
  :main ^:skip-aot globe.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.9"]]}
             :uberjar {:aot :all}})
