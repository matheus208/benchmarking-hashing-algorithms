(defproject benchmark-hashing-algo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories  [["central"  {:url "https://repo1.maven.org/maven2/" :snapshots false}]
                  ["clojars"  {:url "https://clojars.org/repo/"}]
                  ["nu-maven" {:url "s3p://nu-maven/releases/"}]]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [common-crypto      "10.18.0"]
                 [com.stuartsierra/frequencies "0.1.0"]]
  :repl-options {:init-ns benchmark-hashing-algo.core})
