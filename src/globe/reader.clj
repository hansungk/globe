(ns globe.reader
  (:require [globe.buffer :refer :all]
            [clojurewerkz.buffy.core :refer [get-field]]))

(defn region-filename
  "Construct the Anvil file name which contains the chunk that has the x z
  block."
  [x z]
  (str "r." (int (Math/floor (/ x 32))) "." (int (Math/floor (/ z 32))) ".mca"))

(defn chunk-location-offset
  [x z]
  (* 4 (+ (mod x 32) (* 32 (mod z 32)))))

(def chunk-timestamp-offset
  (comp (partial + 4096) chunk-location-offset))

(defn chunk-location
  "Get the offset location at which data of the chunk that contains x z block
  is stored. Location is in bytes."
  [x z]
  (* 4096 (get-field (make-buffer :chunk-loc (chunk-location-offset x z)) :offset)))

(defn chunk-timestamp
  [x z]
  (get-field (make-buffer :chunk-ts (chunk-timestamp-offset x z)) :timestamp))

(defn chunk-data
  [x z]
  (let [pos (chunk-location x z)
        data-length (dec (get-field (make-buffer :chunk-data pos) :length))]
    (to-byte-array (make-bytebuffer (+ pos 5) data-length))))
