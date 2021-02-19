; evaluate this ns form first so the namespace exists in the browser
(ns dev.browser-scratch
  (:require [re-frame.core :as rf]
            [com.widdindustries.demo-app.app :as app]
            [com.widdindustries.firebase.database :as db])) ; <- put cursor before the semicolon and send to REPL


(comment 
  ;exit out of browser REPL
  :cljs/quit
  
  ; get data from db - will be nil initially
  @(rf/subscribe [::db/realtime-value {:path [:users]}])
  
  ; re-render
  (app/mount-components)

  ;control compilation
  (figwheel.main/stop-builds "dev")  ;; stops Figwheel autobuilder for ids
  (figwheel.main/start-builds "dev") ;; starts autobuilder focused on ids
  (figwheel.main/reset)               ;; stops, cleans, reloads config, and starts autobuilder
  (figwheel.main/status)              ;; displays current state of system
  )
