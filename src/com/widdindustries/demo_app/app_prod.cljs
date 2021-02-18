(ns com.widdindustries.demo-app.app-prod
  (:require [com.widdindustries.demo-app.app :as app]))

(.addEventListener
  js/window
  "load"
  (fn []
    (app/init!)))
