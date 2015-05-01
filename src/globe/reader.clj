(ns globe.reader
  (:require [clojurewerkz.buffy.core :refer :all]))

(def fis (java.io.FileInputStream. "r.0.0.mca"))
(def buf (. java.nio.ByteBuffer allocateDirect 16))
(def cin (. fis getChannel))
(def spec-line (spec :bytes-field (bytes-type 16)))

(def spec-chunk-loc (spec :offset (medium-type) :sector-n (byte-type)))

(defn test-read
  []
  (do
    (. cin read buf)
    (. buf flip)
    (let [buffy-buf (compose-buffer spec-line :orig-buffer buf)]
      (seq (get-field buffy-buf :bytes-field)))))

(defn region-filename
  "Construct the Anvil file name which contains the chunk that has the block at x z."
  [x z]
  (str "r." (int (Math/floor (/ x 32))) "." (int (Math/floor (/ z 32))) ".mca"))

(defn chunk-location-offset
  [x z]
  (* 4 (+ (mod x 32) (* 32 (mod z 32)))))
