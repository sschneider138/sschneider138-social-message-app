FROM python:3.12

# setworking dir
WORKDIR /news-service

# copy requirements
COPY requirements.txt  ./

# install dependencies
RUN pip install --no-cache-dir --upgrade -r /news-service/requirements.txt

# copy code
COPY . .

# expose port within container
EXPOSE 8084

# entrypoint
CMD ["uvicorn", "server:app", "--host", "0.0.0.0", "--port", "8084"]