import React, { useEffect, useState } from "react";
import { getTodos } from "../../api/todos";
import Header from "../../components/Header";
import Board from "../../components/Board";
import AddList from "../../components/AddList";
import Card from "../../components/Card";
import "./list.css";

export default function HomePage() {
  const [isToggleList, setIsToggleList] = useState(false);
  const [todos, setTodos] = useState([]);
  console.log(todos);

  const getTodosAPI = async () => {
    try {
      const response = await getTodos();
      response.data.data.forEach(res => {
        res.status = false;
        res.Items.forEach((item => {
          item.isEdit = false
        }))
      })
      setTodos(response.data.data);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getTodosAPI();
  }, []);

  return (
    <>
      <Header>TODOLIST</Header>
      <Board>
        <Card todos={todos} getTodosAPI={() => getTodosAPI()} />
        <div className='add-list'>
          {isToggleList ? (
            <AddList
              handleCancel={() => setIsToggleList(false)}
              getTodosAPI={() => getTodosAPI()}
            />
          ) : (
            <div
              className='add-list-button'
              onClick={() => setIsToggleList(true)}
            >
              <ion-icon name='add-outline'></ion-icon> Add a list
            </div>
          )}
        </div>
      </Board>
    </>
  );
}
