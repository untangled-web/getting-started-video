(ns app.core
  (:require
    app.mutations
    [untangled.client.data-fetch :as df]
    [untangled.client.core :as uc]
    [app.ui :as ui]
    [om.next :as om]))

(defonce app (atom (uc/new-untangled-client
                     :started-callback
                     (fn [{:keys [reconciler]}]
                       (df/load-data reconciler [{:all-items (om/get-query ui/Item)}]
                                     :post-mutation 'fetch/items-loaded)))))

