(ns ^:figwheel-hooks com.widdindustries.demo-app.app-dev
  (:require [com.widdindustries.demo-app.app :as app]))

(defn ^:after-load on-figwheel-reload []
  (.clear js/console)
  ;(re-frame.core/clear-subscription-cache!)
  (app/mount-components))

(.addEventListener
  js/window
  "load"
  (fn []
    (app/init!)))
