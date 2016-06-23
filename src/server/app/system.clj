(ns app.system
  (:require
    [untangled.server.core :as core]
    [app.api :as api]
    [om.next.server :as om]
    [taoensso.timbre :as timbre]
    [com.stuartsierra.component :as c]
    [om.next.impl.parser :as op]))

(defn logging-mutate [env k params]
  (timbre/info "Mutation Request: " k)
  (api/apimutate env k params))

(defn logging-query [{:keys [ast] :as env} k params]
  (timbre/info "Query: " (op/ast->expr ast))
  (api/api-read env k params))

(defrecord Database [items next-id]
  c/Lifecycle
  (start [this] (assoc this
                  :items (atom [])
                  :next-id (atom 1)))
  (stop [this] this))

(defn make-system []
  (core/make-untangled-server
    :config-path "config/demo.edn"
    :parser (om/parser {:read logging-query :mutate logging-mutate})
    :parser-injections #{:db}
    :components {:db (map->Database {})}))
