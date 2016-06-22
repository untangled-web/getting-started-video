(ns app.core
  (:require
    app.mutations
    [untangled.client.core :as uc]
    [app.ui :as ui]))

(defonce app (atom (uc/new-untangled-client)))
(reset! app (uc/mount @app ui/Root "app"))

