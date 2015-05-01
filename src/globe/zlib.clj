(ns globe.zlib
  (:import [java.util.zip Inflater]))

(defn decompress
  [bs]
  (let [decompresser (Inflater.) ; Might have perf issue
        output (byte-array 100000)]
    (. decompresser setInput bs 0 (count bs))
    (let [length (. decompresser inflate output)]
      (. decompresser end)
      (if (= length (count output))
        (throw (java.util.zip.DataFormatException. "Data overflowed byte array"))
        output))))
