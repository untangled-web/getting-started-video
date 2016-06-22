(ns cljs.user
  (:require
    [app.core :refer [app]]
    [untangled.client.core :as core]
    [cljs.pprint :refer [pprint]]
    [devtools.core :as devtools]
    [untangled.client.logging :as log]
    [untangled.client.impl.util :refer [log-app-state]]
    [app.ui :as ui]))

(enable-console-print!)

; Use Chrome...these enable proper formatting of cljs data structures!
(devtools/enable-feature! :sanity-hints)
(devtools/install!)

; Mount the app and remember it.
(reset! app (core/mount @app ui/Root "app"))
