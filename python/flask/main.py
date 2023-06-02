from flask import Flask, jsonify, request
# from flask_cors import CORS
from Logic import EpsilonGreedyRecomm as EGR

app = Flask(__name__)
# CORS(app)


@app.route('/logic', methods=['POST'])
def keyword():
    data = EGR.Bayes_recommendation(request.get_json())
    return jsonify(data), 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8001, debug=True)
