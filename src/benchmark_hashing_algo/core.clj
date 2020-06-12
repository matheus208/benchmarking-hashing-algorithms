(ns benchmark-hashing-algo.core
  (:require [common-crypto.digest :as cr-d]
            [clojure.string :as str]
            [com.stuartsierra.frequencies :as freq])
  (:import [java.security MessageDigest]))

(defmacro bench
  "Times the execution of forms, discarding their output and returning
a long in nanoseconds."
  ([& forms]
   `(let [start#  (System/nanoTime)
          result# ~@forms]

      {:time   [(- (System/nanoTime) start#)]
       :result [result#]})))

(defn map-encrypt
  [word-triplet]
  (let [sha256-result (bench (cr-d/sha-256 (str word-triplet)))
        sha512-result (bench (cr-d/sha-512 (str word-triplet)))
        sha1-result   (bench (cr-d/sha-1 (str word-triplet)))
        crc-32-result (bench (cr-d/crc-32 (str word-triplet)))
        hash-result   (bench (hash word-triplet))

        algorithm     (MessageDigest/getInstance "MD5")
        md5-result    (bench (.digest algorithm (.getBytes (str word-triplet))))]
    {:sha256 sha256-result
     :sha512 sha512-result
     :sha1   sha1-result
     :crc-32 crc-32-result
     :hash   hash-result
     :md5    md5-result}))

(defn map-val [f m]
  (reduce (fn [altered-map [k v]] (assoc altered-map k (f v))) {} m))

(defn -main
  [& args]
  (let [contents    (slurp "words.txt")                     ;; https://github.com/dwyl/english-words
        elements    (str/split-lines contents)
        partitioned (partition 3 1 elements)
        tests       (map map-encrypt partitioned)
        results     (reduce (partial merge-with (partial merge-with into)) tests)
        analysis    (into {} (map-val (fn [v] {:time       (freq/stats (frequencies (:time v)))
                                               :collisions (count (filter #(< 1 (second %)) (frequencies (:results v))))})
                                      results))]
    analysis
    ))
