#convert_base_code

# TensorFlow and tf.keras
import tensorflow as tf
from tensorflow import keras


###########
###########
# handrecognition_model_trained.h5 파일 load 하는 코드 작성
# keras.models.load_model 함수 이용

loaded_model = tf.keras.models.load_model('handrecognition_model_trained.h5')


###########
###########
# load한 handrecognition_model_trained.h5 모델을 .tflite모델로 변환하는 코드 작성

# Convert the model.
converter = tf.lite.TFLiteConverter.from_keras_model(loaded_model)
tflite_model = converter.convert()

# Save the model.
with open('model.tflite', 'wb') as f:
  f.write(tflite_model)
