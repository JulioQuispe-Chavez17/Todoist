'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.bulkInsert(
      'Items',
      [
        {
          name: 'New website codehome',
          TodoId: 1,
          createdAt: new Date(),
          updatedAt: new Date()
        },
        {
          name: 'UX & UI',
          TodoId: 2,
          createdAt: new Date(),
          updatedAt: new Date()
        }
      ]
    )
  },

  down: async (queryInterface, Sequelize) => {
    /**
     * Add commands to revert seed here.
     *
     * Example:
     * await queryInterface.bulkDelete('People', null, {});
     */
  }
};
