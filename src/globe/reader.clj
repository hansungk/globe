(ns globe.reader
  (:require [clojurewerkz.buffy.core :refer :all]))

(def fis (java.io.FileInputStream. "r.0.0.mca"))
(def buf (. java.nio.ByteBuffer allocateDirect 16))
(def cin (. fis getChannel))
(def spec-line (spec :bytes-field (bytes-type 16)))

(defn test-read
  []
  (do
    (. cin read buf)
    (. buf flip)
    (let [buffy-buf (compose-buffer spec-line :orig-buffer buf)]
      (seq (get-field buffy-buf :bytes-field)))))
