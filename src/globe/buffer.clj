(ns globe.buffer
  (:refer-clojure :exclude [read])
  (:require [clojurewerkz.buffy.core :refer :all]
            [clojurewerkz.buffy.frames :refer :all]
            [clojurewerkz.buffy.types.protocols :refer :all]))

(def raf (java.io.RandomAccessFile. "r.0.0.mca", "r"))
(def cin (. raf getChannel))

(defn file-seek
  [bbuf pos]
  (do
    (. raf seek pos)
    (. cin read bbuf)
    (. bbuf flip)))

(defn make-buffer
  ([sp pos]
   (make-buffer sp pos 5))
  ([sp pos len]
   (when-let [bytebuf (. java.nio.ByteBuffer allocateDirect len)]
    (file-seek bytebuf pos)
    (compose-buffer
      (case sp
        :chunk-loc (spec :offset (medium-type) :sector-n (byte-type))
        :chunk-ts (spec :timestamp (int32-type))
        :chunk-data (spec :length (int32-type) :compression (byte-type)))
      :orig-buffer bytebuf))))

(defn make-bytebuffer
  ([len]
   (. java.nio.ByteBuffer allocateDirect len))
  ([pos len]
   (when-let [bytebuf (. java.nio.ByteBuffer allocateDirect len)]
     (file-seek bytebuf pos)
     bytebuf)))

(defn to-byte-array
  [bbuf]
  (when-let [bs (byte-array (. bbuf capacity))]
    (. bbuf get bs)
    bs))
