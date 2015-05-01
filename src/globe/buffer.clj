(ns globe.buffer
  (:refer-clojure :exclude [read])
  (:require [clojurewerkz.buffy.core :refer :all]
            [clojurewerkz.buffy.frames :refer :all]
            [clojurewerkz.buffy.types.protocols :refer :all]))

(def raf (java.io.RandomAccessFile. "r.0.0.mca", "r"))
(def cin (. raf getChannel))

(defn to-byte-array
  "Convert ByteBuffer to byte[]."
  [bbuf]
  (when-let [bs (byte-array (. bbuf capacity))]
    (. bbuf get bs)
    bs))

(defn file-seek
  "Read file from offset pos, store it in bbuf and return it."
  [bbuf pos]
  (do
    (. raf seek pos)
    (. cin read bbuf)
    (. bbuf flip)))

(defn make-bytebuffer
  "Make a ByteBuffer that has capacity len and starts at offset pos in file."
  ([len]
   (. java.nio.ByteBuffer allocateDirect len))
  ([pos len]
   (file-seek (. java.nio.ByteBuffer allocateDirect len) pos)))

(defn make-buffer
  "Make a buffy buffer in accordance to the given spec, byte offset and
  optionally byte length."
  ([sp pos]
   (make-buffer sp pos 5))
  ([sp pos len]
   (compose-buffer
     (case sp
       :chunk-loc (spec :offset (medium-type) :sector-n (byte-type))
       :chunk-ts (spec :timestamp (int32-type))
       :chunk-data (spec :length (int32-type) :compression (byte-type)))
     :orig-buffer (make-bytebuffer pos len))))
