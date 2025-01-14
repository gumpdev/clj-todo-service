(ns clj-todo-service.utils_test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clj-todo-service.utils :refer :all])
  (:import [java.io ByteArrayInputStream]))

(def test-body
  (let [json-str "{\"name\": \"Joao\", \"balance\": 100.5, \"active\": true}"
        byte-array (.getBytes json-str)]
    (io/input-stream (ByteArrayInputStream. byte-array))))
(def valid-test-request
  {:body test-body})
(def invalid-test-request
  {:b "teste"})

(testing "get-body"
  (testing "with valid body"
    (is (=
         (get-body valid-test-request)
                   {:name "Joao"
                    :balance 100.5
                    :active true})))
  (testing "with invalid body"
    (is (=
         (get-body invalid-test-request)
         nil))))

(testing "error"
  (testing "with valid code and messages"
    (is (=
         (error 404 "Not Found")
         {:status 404, :body "{\"msg\":\"Not Found\"}"}))
    (is (=
         (error 400 "Bad Request")
         {:status 400, :body "{\"msg\":\"Bad Request\"}"}))
    (is (=
         (error 403 "Forbidden")
         {:status 403, :body "{\"msg\":\"Forbidden\"}"}))))