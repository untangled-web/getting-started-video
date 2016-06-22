(defproject untangled/demo "1.0.0"
  :description "Demo"
  :url ""
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [org.omcljs/om "1.0.0-alpha37"]
                 [navis/untangled-client "0.5.3" :exclusions [cljsjs/react org.omcljs/om]]
                 [navis/untangled-server "0.5.1"]
                 [navis/untangled-spec "0.3.7-1"]
                 [com.taoensso/timbre "4.3.1"]
                 [commons-codec "1.10"]
                 [binaryage/devtools "0.5.2" :scope "test"]
                 [figwheel-sidecar "0.5.3-1" :exclusions [ring/ring-core joda-time org.clojure/tools.reader com.stuartsierra/component]]]

  :plugins [[lein-cljsbuild "1.1.3"]]

  :source-paths ["dev/server" "src/server"]
  :test-paths ["test/client"]
  :jvm-opts ["-server" "-Xmx1024m" "-Xms512m" "-XX:-OmitStackTraceInFastThrow"]
  :clean-targets ^{:protect false} ["resources/public/js" "target"]

  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src/client" "dev/client"]
                :figwheel     true
                :compiler     {:main                 cljs.user
                               :asset-path           "js/compiled/dev"
                               :output-to            "resources/public/js/compiled/app.js"
                               :output-dir           "resources/public/js/compiled/dev"
                               :optimizations        :none
                               :parallel-build       false
                               :verbose              false
                               :recompile-dependents true
                               :source-map-timestamp true}}
               {:id           "test"
                :source-paths ["test/client" "src/client"]
                :figwheel     true
                :compiler     {:main                 app.suite
                               :output-to            "resources/public/js/specs/specs.js"
                               :output-dir           "resources/public/js/compiled/specs"
                               :asset-path           "js/compiled/specs"
                               :recompile-dependents true
                               :optimizations        :none}}]}

  :repl-options {:init-ns user})
