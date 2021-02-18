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


