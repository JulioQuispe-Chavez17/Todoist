import React, { useState } from "react";
import Title from "../Title";
import TextField from "../TextField";
import "./card.css";
import { deleteTodo, getOneTodo, updateTodo } from "../../api/todos";

export default function Card({ todos, getTodosAPI }) {
  const [editList, setEditList] = useState({
    status: false,
    id: "",
    name: "",
  });

  const toggleEditList = async (id, status) => {
    try {
      const response = await getOneTodo(id);
      if (response.data.message === "success") {
        setEditList({
          ...editList,
          status: status,
          id: response.data.data.id,
          name: response.data.data.name,
        });
      }
    } catch (err) {
      console.log(err);
    }
  };

  const onEnter = async (e, id) => {
    try {
      console.log("e >>>");
      console.log(e);
      if (e.codeKey === 13) {
        const payload = { name: editList.name };
        const response = await updateTodo(id, payload);
        if (response.data.message === "success") {
          setEditList({
            ...editList,
            status: false,
            id: "",
            name: "",
          });
          getTodosAPI();
        }
      }
    } catch (err) {
      console.log(err);
    }
  };

  const deleteTodoAPI = async (id) => {
    try {
      if (window.confirm("Deleted me?")) {
        const response = await deleteTodo(id);
        if(response.data.message === 'success') {
          getTodosAPI()
        }
      }
    } catch (err) {
      console.log(err);
    }
  };

  const onChange = (e) => {
    setEditList({ ...editList, [e.target.name]: e.target.value });
  };

  return (
    <>
      {todos.map((todo, i) => {
        <div>
          <div className="list" key={i}>
            <div className="lists-card">
              {editList.status && editList.id === todo.id ? (
                <TextField
                  name="name"
                  value={editList.name}
                  className="list-title-textarea"
                  onChange={onChange}
                  deleteList={() => deleteTodoAPI(editList.id)}
                  handleCancel={() =>
                    setEditList({
                      ...editList,
                      status: false,
                      id: "",
                      name: "",
                    })
                  }
                  onEnter={(e) => onEnter(e, editList.id)}
                />
              ) : (
                <Title onClick={() => toggleEditList(todo.id, true)}>
                  {todo.name}
                </Title>
              )}
              {todo.Items.map((item) => {
                <div className="card" key={item.id}>
                  {item.name}
                </div>;
              })}

              <div className="toggle-add-card">
                <ion-icon name="add-outline"></ion-icon> Add a card
              </div>
            </div>
          </div>
        </div>;
      })}
    </>
  );
}
