(ns globe.reader-test
  (:require [clojure.test :refer :all]
            [globe.reader :refer :all]))

(deftest region-filename-test
  (are [x z s] (= (region-filename x z) s)
       30 -3 "r.0.-1.mca"
       70 -30 "r.2.-1.mca"))

(deftest chunk-location-offset-test
  (are [x z ofs] (= (chunk-location-offset x z) ofs)
       0 0 0
       -1 -1 4092))
