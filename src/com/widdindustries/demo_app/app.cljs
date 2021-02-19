(ns com.widdindustries.demo-app.app
  (:require [reagent.core :as reagent]
            [com.widdindustries.demo-app.view :as view]))

(defn app-container [id]
  (js/document.getElementById id))

(defn mount-components []
  (when-let [container (app-container "app")]
    (reagent/render [view/app-view] container)))

(defn init! []
  (js/console.log "Initializing")
  (mount-components))

(defn ^:after-load on-figwheel-reload []
  (.clear js/console)
  ;(re-frame.core/clear-subscription-cache!)
  (mount-components))

(.addEventListener
  js/window
  "load"
  (fn []
    (init!)))


