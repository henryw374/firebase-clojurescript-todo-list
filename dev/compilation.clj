(ns compilation
  (:require [figwheel.main.api :as figwheel]
            [cljs.build.api :as cljs]
            [clojure.java.shell :as sh]))

(defn stop-live-compilation []
  (figwheel/stop-all))

(def debug-opts
  {:pseudo-names true
   :pretty-print true
   ;:source-map   true
   })

(defn clean []
  (sh/sh "rm" "-rf" "firebase/public/cljs-out")
  (sh/sh "rm" "-rf" "prod-target")
  )

(defn prod-build []
  (stop-live-compilation)
  (clean)
  (cljs/build
    (->
      {:optimizations :advanced
       :infer-externs true
       :main          'com.widdindustries.demo-app.app-prod
       :process-shim  false
       :output-dir    "prod-target"
       :output-to     "firebase/public/cljs-out/main.js"}
      ;(merge debug-opts)
      )))

(defn start-live-compilation []
  (figwheel/start {:mode :serve}
    {:id      "dev"
     :options {:main 'com.widdindustries.demo-app.app-dev
               :output-to    "firebase/public/cljs-out/main.js"
               :output-dir   "firebase/public/cljs-out"
               :repl-verbose false}
     
     :config  {:auto-testing        true
               :open-url            false
               :watch-dirs          ["src"]
               :css-dirs            ["firebase/public/css"]
               :ring-server-options {:port 9502}}}))

(defn cljs-repl []
  (figwheel/cljs-repl "dev"))

(comment
  
  (start-live-compilation)
  (stop-live-compilation)
  (cljs-repl)
  (prod-build)
  
  )
