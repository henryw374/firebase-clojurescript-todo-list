(ns compilation
  (:require [shadow.cljs.devtools.api :as api]
            [shadow.cljs.devtools.server :as server]
            [shadow.cljs.build-report :as build-report]
            [clojure.java.shell :as sh]))

(defn restart []
  (server/stop!)
  (server/start!))

(defn clean [path]
  (sh/sh "rm" "-rf" (str "./firebase/public/" path)))

(defn delete-cache []
  (sh/sh "rm" "-rf" (str "./.shadow-cljs/builds")))

#_(defn devcards []
  (api/stop-worker :app-dev)
  (api/stop-worker :devcards)
  (clean "devcards/js")
  (api/watch :devcards {:verbose true}))

(defn prod-build []
  (api/stop-worker :app-dev)
  (api/stop-worker :devcards)
  (clean "cljs-out")
  (api/release :app))

(defn watch []
  (api/stop-worker :app-dev)
  (clean "cljs-out")
  (api/watch :app-dev {:verbose false}))

(defn start-live-compilation []
  (restart)
  (watch))

(defn cljs-repl []
  (api/repl :app-dev))

(comment
  (restart)
  (delete-cache)
  (api/watch :test {:verbose false})
  (api/stop-worker :test)
  (api/once :npm)
  (watch)
  (api/watch-set-autobuild! :app-dev false)
  (api/watch-set-autobuild! :app-dev true)
  (cljs-repl)
  :cljs/quit
  
  (prod-build)
  (build-report/-main :app "report.html")

  ;(devcards)
  ;(api/stop-worker :devcards)
  ;(api/repl :devcards)


  )
