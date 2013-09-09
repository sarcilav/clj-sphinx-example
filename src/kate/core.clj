(ns kate.core)

(import
 '(edu.cmu.sphinx.frontend.util Microphone)
 '(edu.cmu.sphinx.recognizer Recognizer)
 '(edu.cmu.sphinx.result Result)
 '(edu.cmu.sphinx.util.props ConfigurationManager))

(defn new-config
  ([] (new-config "kate.config.xml"))
  ([config-path] (new ConfigurationManager (clojure.java.io/resource config-path))))

(defn get-recognizer [config]
  (let [recognizer (.lookup config "recognizer")]
    (do
      (.allocate recognizer)
      recognizer)))

(defn get-microphone [config]
  (let [microphone (.lookup config "microphone")]
    (do
      (.startRecording microphone)
      microphone)))

(defn listen [recognizer microphone]
  (let [result (.recognize recognizer)]
    (if (not= result nil)
      (println (str "You said:" (.getBestResultNoFiller result)))
      (println "I can't hear what you said."))))

(def cm (new-config))
(def rec (get-recognizer cm))
(def mic (get-microphone cm))

(while true (listen rec mic))
