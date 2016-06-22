(ns app.mutations
  (:require [untangled.client.mutations :as m]))

(defmethod m/mutate 'app/choose-tab [{:keys [state]} _ {:keys [tab]}]
  {:action (fn [] (swap! state assoc :tabs [tab 1]))})

