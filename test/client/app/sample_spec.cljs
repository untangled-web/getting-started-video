(ns app.sample-spec
  (:require [untangled-spec.core :refer-macros [specification behavior assertions]]))

(specification "TODO Spec"
  (behavior "Stuff shoule work"
    (assertions
      "like math"
      (+ 1 1) => 2)))
