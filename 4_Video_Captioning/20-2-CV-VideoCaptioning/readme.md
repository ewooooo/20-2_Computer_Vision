# Video Captioning
LSTM기반의 Seq2Seq Model을 사용한 Video Captioning

__수정된 코드가 있으므로, LMS에서 다운받은 코드가 아닌 GitHub에서 코드를 재 다운로드 하여 사용해주세요__   
__지속적으로 GitHub readme를 업데이트 하고 있습니다. 오류가 생기면 우선 Readme를 한번 읽어보시고, 질문 주시면 감사하겠습니다.__

### 자주 질문하는 오류(이외에도 아래 readme 전체를 자세히 읽으시면 관련 내용이 있습니다)   

- GPU를 쓸 때는 각자의 Nvidia 그래픽 드라이버에 맞춰서 CUDA와 CuDNN을 설치해주셔야 합니다. 저는 CUDA 9.2를 사용했었습니다.   

- 환경 셋팅 시, 여러 개의 tensorflow가 설치되어 있는 분들이 있습니다. GPU가 있는 분들은 -gpu버전을 설치해주셔도 무방하지만, 없으신 분들은 requirement에서 "-gpu"를 -cpu로 변경하고 설치해주세요. 밑에까지 안읽고 그냥 설치하시는 분들이 많아 한번 더 명시해드립니다.

- 어텐션 참고 링크 : (https://www.tensorflow.org/tutorials/text/image_captioning)
------------

# Requirement

### __0. 파이참 로딩__ ###   
파이참 로딩 때 간혹 빈 프로젝트를 생성하고, 코드를 엎어씌우는 과정에서 오류가 나시는 분들이 있습니다.
프로젝트를 따로 만들지 않고, Pycharm > File > Open 탭으로 프로젝트를 열거나 터미널 상에서 접근하시면 큰 오류가 없을 것 같습니다.

### __1. 필요 라이브러리 설치__ ###   
( Anaconda 환경 생성 후, 설치 진행)   
- python 3.5(or 3.6) / Tensorflow 1.15.0    
버전 오류가 있으신 분들은 python 3.6 버전을 설치해서 진행해보시기 바랍니다.
```
pip install -r requirements.txt
```
- Tensorflow는 GPU 유무에 따라 requirements 파일에서 "-gpu"를 삭제, 추가하실 수 있습니다.   

### __2. 학습 데이터 다운로드__ ###   

[다운로드 링크](https://drive.google.com/file/d/1WV12AvojTshyfsDAkmDhkxw7qUcGPgGm/view?usp=sharing)   
--> 이미 LMS로부터 받으신 분은 해당 zip파일 그대로 사용하시면 됩니다. 혹시나 LMS에 전체 데이터가 다 올라가지 않을까 염려되어 링크를 올려둔 것이니, 참고하시기 바랍니다. 데이터.zip파일의 용량은 약 2.24GB이며, 압축 해제했을 경우엔 5.18GB 정도의 사이즈를 갖고 있습니다. 확인해보시고 문제 있으시면 다시 다운로드 받아 사용하세요.

### __3. 수정해야 하는 파일들__ ###
blue_eval.py, model_train.py, model_test.py 세 가지 파일 내 본인 경로로 변경   

- 경로 설정 시, Window는 "\\\\" 를 Ubuntu에서는 "/"를 유의해서 사용해주세요.
- 각 모델 별 설정된 하이퍼 파라미터는 테스트 용으로 기입된 숫자이며, 절대 잘 되는 모델을 위한 하이퍼 파라미터가 아닙니다.본인 컴퓨터 사양에 맞춰 여러 번 실험해보고 최고의 성능을 내는 하이퍼 파라미터를 결정한 후, 실험에 사용하시기 바랍니다.   
- 코드를 실행할 때 너무 오래걸리거나 CPU, GPU용량이 부족할 경우 batch size를 줄여 돌리시기 바랍니다.   

------------

## Project Requirement
From (https://github.com/SidSong01/seq2seq_video2text)   

### __1. 다운받은 데이터를 data폴더로 이동__ ###
![image1](./directory.jpg)

### __2. 학습진행__ ###   
  ```
  python model_train.py
  ```
### __3. 성능 확인__ ###

  ```
  python model_test.py
  ```
  final_output.txt파일 생성 여부 확인 
  
  ```
  python bleu_eval.py
  ```
  Bleu Score 확인   
    
### __4. Tensorboard 확인__ ###   

   학습 과정 중 일어난 Loss변화 또는 학습 변화를 확인하고 싶을 때는
   logs/train(test)폴더 안에 있는 tensorboard 파일로 변화율을 확인할 수 있음
   ```
   tensorboard --logdir=./logs/train(test)
   ```
   웹 브라우저에서 http://localhost:6006/ 으로 확인   
   
