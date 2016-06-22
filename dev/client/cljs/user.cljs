(ns cljs.user
  (:require
    [app.core :refer [app]]
    [cljs.pprint :refer [pprint]]
    [devtools.core :as devtools]
    [untangled.client.impl.util :as util]))

(enable-console-print!)

; Use Chrome...these enable proper formatting of cljs data structures!
(devtools/enable-feature! :sanity-hints)
(devtools/install!)

(def log-app-state (partial util/log-app-state app))


